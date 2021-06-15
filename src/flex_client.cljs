(ns flex-client
  (:require [ajax.core :refer [GET]]))

(defn get-elem-val [id]
  (.-value (.getElementById js/document id)))

(defn is-scrambled [scrambled-id word-id]
  (let [scrambled-val (get-elem-val scrambled-id)
        word-val (get-elem-val word-id)]
    (.log js/console scrambled-val word-val)
    (GET "http://localhost:3000/is-scrambled"
         {:params {:scrambled scrambled-val
                   :word word-val}
          :handler #(.log js/console (str %))})
    ))
