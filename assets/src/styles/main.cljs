(ns styles.main
  (:require [garden.core :refer [css]]
            [styles.util :as u]))

(u/inject!
 (css
  [:#join-channel-prompt
   {:display "flex"
    :width "500px"
    :margin "auto"
    :alignment "center"}]

  [:#message-input
   {:display "flex"
    :position "absolute"
    :width "99%"
    :bottom "10px"
    :flex-direction "row"}]

  [:.msg-view
   {:display "flex"
    :flex-direction "column"
    :overflow "auto"}]

  [:.msg-view-uname
   {:font-size "12px"}]

  [:.msg-view-row
   {:width "60%"}]

  [:msg-view-msg
   {:font-size "28px"
    :word-wrap "break-word"}]

  [:.msg-view-row-other
   {:float "left"
    :text-align "left"}]

  [:.msg-view-row-self
   {:float "right"
    :text-align "right"}]

  [:#message-input :button
   {:margin-left "8px"}]))

