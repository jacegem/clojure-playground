(ns chap-3)


(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc3 (inc-maker 3))

(inc3 7)

(defn dec-maker
  [dec-by]
  #(- % dec-by))

(def dec9 (dec-maker 9))

(dec9 10)

(defn dec-maker
  [dec-by]
  #(- % dec-by))

(defn mapset [f coll]
  (set
   (map f coll)))

(mapset inc [1 1 2 2])