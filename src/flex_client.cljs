(ns flex-client
  (:require [ajax.core :refer [GET]]
            [reagent.core :as r]
            [reagent.dom :as rd]))

(defn get-elem-val [id]
  (.-value (.getElementById js/document id)))

(defn is-scrambled
  "str, str, str -> nil
  Gets the ID of two input elements and one text element, checks if the value
  is scramble using the back-end, and replaces the text with the answer."
  [scrambled-id word-id answer-id]
  (let [scrambled-val (get-elem-val scrambled-id)
        word-val (get-elem-val word-id)]
    (GET "/is-scrambled"
         {:params {:scrambled scrambled-val
                   :word word-val}
          :handler #(-> (.getElementById js/document answer-id)
                        .-textContent
                        (set! (:scrambled? %)))})))

(defn mk-text-input [id-name]
  [:input.form-control.mb-2.mr-sm-2
   {:type "text" :id id-name :name id-name :placeholder id-name}])

(defn form-component []
  [:form.form-inline
   (mk-text-input "scrambled")
   (mk-text-input "word")])

(defn page []
  [:div.container.pt-3
   [:div.jumbotron>h1 "Scrambled?"]
   [form-component]
   [:p "Is it scrambled?"]])

(defn init []
  (rd/render [page]
             (.getElementById js/document "primary-container")))

(init)
