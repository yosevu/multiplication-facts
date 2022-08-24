(ns multiplication-facts.core
  (:require [clojure.string :refer [join]]))

(def limit 13)

(defn get-factors
  []
  (take limit (iterate inc 0)))

(defn get-problem
  [n fact]
  ;; (->> fact
  ;;      #(conj (list n) %)
  ;;      (join "x")))
  (join "x" (conj (list n) fact)))

(defn get-solution
  [n fact]
  (str (* n fact)))

(defn build-problem
  [fact]
  (fn [n]
    (join "," (cons (get-problem n fact) (list (get-solution n fact))))))

(defn build-facts
  [fact]
  (->> (get-factors)
       (map (build-problem fact))
       (join "\n")))

(defn get-fact
  [facts fact]
  (conj facts (build-facts fact)))

(defn get-facts
  []
  (reduce get-fact [] (get-factors)))

(defn write-file
  [content]
  (spit "multiplication-facts.csv" content))

(defn multiplication-facts
  []
  (->> (get-facts)
       (join "\n")
       write-file))

(defn -main
  [& args]
  (multiplication-facts))

(comment
  (-main)
  (multiplication-facts)
  (clojure.pprint/pprint (multiplication-facts))
  (println (count (multiplication-facts))))
