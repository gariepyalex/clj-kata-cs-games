(ns js-compe.q4)

(defn message-data
  [message]
  (let [lenght (->> message
                    (drop 8)
                    (take 32)
                    (#(Integer/parseInt % 2)))]
    (->> message
         (drop 40)
         (take lenght))))


(defmulti parse
  (fn [message]
    (Integer/parseInt (take 8 message) 2)))


(defmethod parse 0
  (fn [message]
    (let [data (message-data message)]
      {:name (parse data)})))

