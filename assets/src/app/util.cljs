(ns app.util
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]))

(defn event->value
  [event]
  (-> event .-target .-value))

(defn resetter
  [state]
  (fn [event]
    (reset! state (event->value event))))

(defn on-key-down
  [k callback]
  (fn [e]
    (when (= k (.-key e))
      (callback))))

(defn ->clj
  [js-obj]
  (js->clj js-obj :keywordize-keys true))

(defn re-class
  [& args]
  {:class
   (->> (map (fn [x]
               (match x
                      [k t f] (if k (name t) (name f))
                      [k cl] (when cl (name k))
                      k (name k)))
             args)
        (filter some?)
        (str/join " "))})
