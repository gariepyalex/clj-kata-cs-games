(ns js-compe.q1
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))


(def command-file (slurp (io/file (io/resource "1_input_generate.txt"))))


(defn action-to-keyword
  [action-str]
  (case action-str
    "turn on"  :turn-on
    "turn off" :turn-off
    "toggle"   :toggle))


(defn parse-command
  [command-str]
  (let [[_ action x1 y1 x2 y2] (re-matches #"([a-z\s]+) (\d+),(\d+) to (\d+),(\d+)"
                                           command-str)]
    [(action-to-keyword action)
     (Integer/parseInt x1)
     (Integer/parseInt y1)
     (Integer/parseInt x2)
     (Integer/parseInt y2)]))


(defn parse-commands
  [commands]
  (->> (s/split-lines commands)
       (map parse-command)))


(def initial-state
  (into {}
        (for [x (range 1000) y (range 1000)]
          [[x y] :off])))


(defmulti reduce-update-state
  (fn [state command]
    (first command)))

(defmethod reduce-update-state :toggle
  [state [_ coordinate]]
  (update state coordinate #(if (= % :on) :off :on)))

(defmethod reduce-update-state :turn-on
  [state [_ coordinate]]
  (assoc state coordinate :on))

(defmethod reduce-update-state :turn-off
  [state [_ coordinate]]
  (assoc state coordinate :off))

(defn update-state
  [state commands]
  (reduce reduce-update-state
          state
          commands))

(defn expand-command
  [[action x1 y1 x2 y2]]
  (for [x (range x1 (inc x2)) y (range y1 (inc y2))]
    [action x y]))

(defn count-on
  [state]
  (->> state
       (filter (fn [[k v]] (= :on v)))
       count))

(comment
  (let [commands (->> (parse-commands command-file)
                      (mapcat expand-command))]
    (count-on (update-state initial-state commands))))

;; 325
