(ns flex-client
  (:require [ajax.core :refer [GET]]))

(defn get-elem-val [id]
  (.-value (.getElementById js/document id)))

(defn is-scrambled
  "str, str, str -> nil
  Gets the ID of two input elements and one text element, checks if the value
  is scramble using the back-end, and replaces the text with the answer."
  [scrambled-id word-id answer-id]
  (let [scrambled-val (get-elem-val scrambled-id)
        word-val (get-elem-val word-id)]
    ;; TODO: parameterize the back-end address.
    (GET "http://localhost:3000/is-scrambled"
         {:params {:scrambled scrambled-val
                   :word word-val}
          :handler #(-> (.getElementById js/document answer-id)
                        .-textContent
                        (set! (:scrambled? %)))})))
