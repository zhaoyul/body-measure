(defproject body-measure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.7.0"]
                 [org.clojure/data.codec "0.1.1"]
                 [org.clojure/tools.cli "0.3.5"]]
  :main body-measure.core
  :aot [body-measure.core])
