(ns flex-client
  (:require [ajax.core :refer [GET]]))

(defn get-elem-val [id]
  (.-value (.getElementById js/document id)))

(defn is-scrambled [scrambled-id word-id]
  (let [scrambled-val (get-elem-val scrambled-id)
        word-val (get-elem-val word-id)]
    (println scrambled-val word-val)
    (js/alert scrambled-val word-val)
    ))
