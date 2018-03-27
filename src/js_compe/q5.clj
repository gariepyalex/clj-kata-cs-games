(ns js-compe.q5
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(def walk-seq (seq (slurp (io/file (io/resource "5_input.txt")))))

(def initial-state {:visited #{[0 0]}
                    :location [0 0]})

(defn new-location
  [[x y] command]
  (condp = command
        \^ [x (inc y)]
        \< [(dec x) y]
        \> [(inc x) y]
        \v [x (dec y)]))

(defn walk
  [state walk-seq]
  (reduce (fn [state command]
            (-> state
                (update :location new-location command)
                (#(update % :visited conj (:location %)))))
          state
          walk-seq))



(comment
  (count (:visited (walk initial-state walk-seq))))
;; 2565


(comment
  (let [pin1-walk (take-nth 2 walk-seq)
        pin2-walk (take-nth 2 (rest walk-seq))
        state1 (walk initial-state pin1-walk)]
    (count (:visited (walk (assoc state1 :location [0 0]) pin2-walk)))))
