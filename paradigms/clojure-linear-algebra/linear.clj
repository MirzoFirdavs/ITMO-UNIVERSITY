(defn isVector [v] (and (vector? v) (every? number? v)))
(defn isMatrix [m]
  (and
    (coll? m)
    (every? isVector m)
    (or
      (every? #(= (vector) %) m)
      (let [len (.count (first m))] (every? #(== len (.count %)) m)))))

(defn foldLeft [f, pre]
  (fn [& items]
    {:pre [(pre (first items))]}
    (apply
      (fn [zero & items]
        (if (empty? items)
          zero
          (recur (f zero (first items)) (rest items)))) items)))

(defn v*s [v & s] {pre [(isVector v) (every? number? s)]} (mapv (partial * (apply * s)) v))

(defn vm [f, pre]
  (fn [& items]
    (if (== (.count items) 1)
      (if (= (first items) (vector))
        (vector)
        (mapv f (first items)))
      (apply (foldLeft
               (fn [l r]
                 {:pre [(and (pre r) (== (.count r) (.count l)))]}
                 (mapv f l r)) pre) items))))

(def v+ (vm + isVector))
(def v- (vm - isVector))
(def v* (vm * isVector))
(def vd (vm / isVector))

(defn scalar [& items]
  (apply + (apply v* items)))

(defn det [x11 x12 x21 x22]
  (- (* x11 x22) (* x12 x21)))

(def vect
  (foldLeft
    (fn [l r]
      {:pre [(and (isVector r) (== (.count l) (.count r) 3))]}
      (vector
        (det (nth l 1) (nth l 2) (nth r 1) (nth r 2))
        (- (det (nth l 0) (nth l 2) (nth r 0) (nth r 2)))
        (det (nth l 0) (nth l 1) (nth r 0) (nth r 1)))) isVector))

(def m+ (vm v+ isMatrix))
(def m- (vm v- isMatrix))
(def m* (vm v* isMatrix))
(def md (vm vd isMatrix))

(def m*s
  (foldLeft
    (fn [m s]
      {:pre [(number? s)]}
      (mapv
        (fn [v]
          (v*s v s)) m)) isMatrix))

(def m*v
  (foldLeft
    (fn [m v]
      {:pre [(and (isVector v) (== (.count v) (.count (first m))))]}
      (mapv
        (partial scalar v) m)) isMatrix))

(defn transpose [m]
  {:pre [(isMatrix m)]}
  (apply mapv vector m))

(def m*m
  (foldLeft
    (fn [l r]
      {:pre [(and (isMatrix r) (== (.count r) (.count (first l))))]}
      (transpose
        (mapv (fn [v] (mapv (partial scalar v) l)) (transpose r)))) isMatrix))