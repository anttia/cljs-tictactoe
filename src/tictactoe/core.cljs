(ns ^:figwheel-always tictactoe.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defn new-board [n]
  (vec (repeat n (vec (repeat n 0)))))

(defonce app-state
  (atom {:text "Welcome to tic tac toe"
         :board (new-board 3)}))

(defn tictactoe []
  [:center
    [:h1 (:text @app-state)]
      [:svg
        {:view-box "0 0 3 3"
         :width 500
         :height 500}
         (for [i (range (count (:board @app-state)))
               j (range (count (:board @app-state)))]
               (case (get-in @app-state [:board j i])
                  0 [blank i j]
                  1 [circle i j])
                [:rect
                  {:width 0.9
                    :fill (if (zero?) "green" "yellow")
                    :height 0.9 :x i :y j
                   :on-click (fn rect-click[e]
                     (prn "You clicked me!" i j)
                     (prn (swap! @app-state update-in :board)))}
                  ])]
      ])

(reagent/render-component [tictactoe]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
