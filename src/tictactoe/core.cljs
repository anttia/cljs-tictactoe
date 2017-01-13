(ns ^:figwheel-always tictactoe.core
  (:require [reagent.core :as reagent :refer [atom]]))

; tutorial: https://www.youtube.com/watch?v=pIiOgTwjbes&t=443s

(enable-console-print!)

(defn new-board [n]
  (vec (repeat n (vec (repeat n 0)))))

(defonce app-state
  (atom {:text "Welcome to tic tac toe"
         :board (new-board 3)}))

(defn compute-move []
  (swap! app-state assoc-in [:board 0 0] 2))

(defn blank [i j]
  [:rect
    {:width 0.9
     :height 0.9
     :fill "gray"
     :x i
     :y j
     :on-click (fn rect-click [e]
       (prn (swap! app-state update-in [:board i j] inc))
       (compute-move))}
  ])

(defn circle [i j]
  [:circle
    { :r 0.45
      :fill "green"
      :cx (+ 0.5 i)
      :cy (+ 0.5 j)}])

(defn cross [i j]
  [:g { :stroke "darkred" :line-width 0.2 :stroke-linecap "round"}
    [:line {:x1 0 :y1 0 :x2 1 :y2 1}]
    [:line {:x1 1 :y1 0 :x2 0 :y2 1}]])

(defn tictactoe []
  [:center
    [:h1 (:text @app-state)]
      (into [:svg
        {:view-box "0 0 3 3"
         :width 500
         :height 500}]
         (for [i (range (count (:board @app-state)))
               j (range (count (:board @app-state)))]
               (case (get-in @app-state [:board i j])
                  0 [blank i j]
                  1 [circle i j]
                  2 [cross i j])))
      ])

(reagent/render-component [tictactoe]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  (swap! app-state update-in assoc-in [:board 0 0] 2)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
