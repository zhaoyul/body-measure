(ns body-measure.core
  (:require [clj-http.client :as client]
            [clojure.data.codec.base64 :as b64-codec]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class)
  )

(def cli-options
  ;; An option with a required argument
  [["-i" "--input input.jpg"
    :default "input.jpg"]
   ;; A non-idempotent option
   ["-o" "--output output.jpg"
    :default "output.jpg"]
   ])


(defn decode-str [s]
  (b64-codec/decode (.getBytes s)))

(defn write-img! [file-name b64]
  (clojure.java.io/copy
   (decode-str b64)
   (java.io.File. file-name)))


(def base-url "https://api-cn.faceplusplus.com/humanbodypp/beta/segment")
(def api-key "NftZDbTYUkRnnkxKuWlQMnn_ks5G3ITn")
(def api-secret "cnMO24ie4I41m45ns8Y0d5_JabrWHfXs")


(defn string-respond [input-file]
  (str (clojure.string/replace
        (:body
         (client/post base-url {:multipart [{:name "api_key" :content api-key}
                                            {:name "api_secret" :content api-secret}
                                            {:name "image_file" :content (clojure.java.io/file input-file)}]}))
        #":"
        "")))

(defn result [input-file]
  (let [result-map (read-string (string-respond input-file))]
    (get result-map "result")))


(defn -main [& args]
  (let [options (parse-opts args cli-options)
        input-file (get-in options [:options :input] )
        output-file (get-in options [:options :output])]
    (->> (result input-file)
         (write-img! output-file))
    ))
