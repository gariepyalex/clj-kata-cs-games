(ns js-compe.q3
  (:require [clojure.string :as s]))

(def message (s/join "\n"
                     ["palneic"
                      "bmzkbmd"
                      "czusoem"
                      "cheucht"
                      "innmwbe"
                      "fcglxez"
                      "rktvgpk"
                      "csgozly"
                      "gsscmes"
                      "uxhajls"
                      "bnghmpd"
                      "ervaddi"
                      "hjpxuru"
                      "xomryqb"
                      "zwmabmh"]))


(defn column
  [lines i]
  (map #(nth % i) lines))


(defn decode
  [message]
  (let [lines (s/split-lines message)
        n-col (count (first lines))
        cols (map (partial column lines) (range n-col))]
    (apply str (->> cols
                    (map frequencies)
                    (map #(sort-by second %))
                    (map reverse)
                    (map #(first (first %)))))))
