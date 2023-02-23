(ns java-io
  (:require [clojure.java.io :as io]))

(def data-file (io/resource "day1.sample.txt"))

(defn -main []
  (println (slurp data-file)))