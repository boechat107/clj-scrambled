(ns flex-client
  (:require [ajax.core :refer [GET]]
            [reagent.core :as r]
            [reagent.dom :as rd]))

(letfn [(on-change [elem id-name atom-data]
          (let [elem-val (-> elem .-target .-value)
                request-data (-> @atom-data
                                 (select-keys [:word :scrambled])
                                 (assoc (keyword id-name) elem-val))]
            (GET "/is-scrambled"
                 {:params request-data
                  :handler #(reset! atom-data
                                    (->> (:scrambled? %)
                                         str
                                         (assoc request-data :answer)))})))]
  (defn mk-text-input [id-name aval]
    [:input.form-control.mb-2.mr-sm-2
     {:type "text" :id id-name :name id-name :placeholder id-name
      :value (@aval (keyword id-name))
      :on-change #(on-change % id-name aval)}]))

(defn form-component []
  (let [atom-data (r/atom {:scrambled "" :word "" :answer ""})]
    (fn []
      [:div
       [:div.jumbotron>h1 "Scrambled?"]
       [:form.form-inline
        (mk-text-input "scrambled" atom-data)
        (mk-text-input "word" atom-data)]
       [:p "Is it scrambled? "
        [:span.text-success (:answer @atom-data)]]])))

(defn init []
  (rd/render [form-component]
             (.getElementById js/document "primary-container")))

(init)
