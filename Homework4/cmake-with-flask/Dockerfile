FROM alpine:3.15

RUN apk update && apk --no-cache add cmake make g++ python3 py3-pip && \
    pip3 install flask && \
    addgroup -S techproggroup && adduser -S techproguser -G techproggroup && \
    mkdir /app && chown -R techproguser /app

USER techproguser

COPY . /app
WORKDIR /app

RUN mkdir build && cd build && cmake .. && make && cd ..

CMD [ "python3", "-m" , "flask", "run", "--host=0.0.0.0"]

