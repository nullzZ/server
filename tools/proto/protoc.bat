echo 生成协议
protoc -I=./proto --java_out=../../src/main/java ./proto/*.proto
echo '协议生成完毕。