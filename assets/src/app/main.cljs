(ns app.main
  (:require [reagent.core :as r]
            [cljs.core.async :as a]
            [app.socket :as s]
            [app.util :as u]))

(defonce app-state
  (r/atom {:message-text ""
           :channel-name ""
           :socket nil
           :channel nil
           :messages []
           :username ""}))

(def socket (r/cursor app-state [:socket]))
(def channel (r/cursor app-state [:channel]))
(def message-text (r/cursor app-state [:message-text]))
(def messages (r/cursor app-state [:messages]))
(def username (r/cursor app-state [:username]))

(def ^:private id-seq (atom 0))
(defn get-id [] (swap! id-seq inc))

(defn send []
  (s/send! @channel @message-text)
  (reset! message-text ""))

(defn handle-message
  [message]
  (swap! messages conj (u/->clj message)))

(defn join
  []
  (let [socket* (swap! socket s/create @username)]
    (swap! socket s/connect!)
    (reset! channel (s/join! socket* "room:lobby" @username))
    (swap! channel s/on! "room:lobby:new_message" handle-message)))

(defn join-channel-prompt []
  [:div#join-channel-prompt
   [:input {:type "text"
            :class "form-control"
            :placeholder "Pick a username"
            :value @username
            :on-key-press (when-not (empty? @username)
                            (u/on-key-down "Enter" join))
            :auto-complete "off"
            :auto-focus true
            :on-change (u/resetter username)}]
   [:button {:class "btn btn-outline-success"
             :on-click join
             :disabled (empty? @username)}
    "join"]])

(defn message-view []
  [:div#msg-view-wrapper.fill
   [:ul#msg-view.fill
    (doall
     (for [{:keys [ts user message]} @messages]
       ^{:key (get-id)}
       [:li (u/re-class
             :msg-view-row
             [(= user @username)
              :msg-view-row-self
              :msg-view-row-other])
        [:span.msg-view-uname user]
        [:div.msg-view-msg message]]))]])

(defn message-input []
  [:div#msg-input
   [:input {:type "text"
            :class "form-control"
            :placeholder "Send a message..."
            :value @message-text
            :on-key-press (when-not (empty? @message-text)
                            (u/on-key-down "Enter" send))
            :auto-complete "off"
            :auto-focus true
            :on-change (u/resetter message-text)}]
   [:button {:class "btn btn-outline-success"
             :disabled (empty? @message-text)
             :on-click send}
    "send"]])

(defn channel-view []
  [:div#channel-wrapper.fill
   (message-view)
   (message-input)])

(defn app []
  (if (some? @channel)
    (channel-view)
    (join-channel-prompt)))

(defn ^:dev/after-load main!
  []
  (r/render
   [app]
   (.getElementById js/document "app")))
