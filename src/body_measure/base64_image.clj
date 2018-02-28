(ns base64-image
  (:require [clojure.data.codec.base64 :as b64-codec]))

(defn write-img! [b64]
  (clojure.java.io/copy
   (decode-str b64)
   (java.io.File. (str "output" "." (b64-ext b64)))))

(defn decode-str [s]
  (b64-codec/decode (.getBytes s)))

(defn in?
  "true if the seq coll contains the element el"
  [coll el]
  (some #(= el %) coll))

(defn b64-ext [s]
  (if-let [ext (second (first (re-seq #"data:image/(.*);base64.*" s)))]
    (if (in? ["png" "jpeg"] ext)
      ext
      (throw (Exception. (str "Unsupported extension found for image " ext))))
    (throw (Exception. (str "No extension found for image " s)))))

(defn chop-header [s]
  (nth (first (re-seq #"(data:image/.*;base64,)(.*)" s)) 2))
