(ns js-compe.q2
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))



(def ship-file (slurp (io/file (io/resource "2_input.txt"))))

(def ships (->> (s/split-lines ship-file)
                (map #(Integer/parseInt %))
                (sort)
                (reverse)
                (vec)))

(defn count-ship-seating
  [ships n-pin]
  (if (or (and (= 1 (count ships))
               (= (first ships) n-pin))
          (= 0 n-pin))
    1
    (if (not (empty? ships))
      (let [ship (first ships)]
        (if (>= n-pin ship)
          (+ (count-ship-seating (rest ships) (- n-pin ship))
             (count-ship-seating (rest ships) n-pin))
          (count-ship-seating (rest ships) n-pin)))
      0)))

(comment (count-ship-seating ships 150))
;; 654
