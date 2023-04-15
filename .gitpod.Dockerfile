FROM gitpod/workspace-base

USER gitpod

RUN \
    wget -O- https://apt.corretto.aws/corretto.key | sudo apt-key add - && \
    sudo add-apt-repository 'deb https://apt.corretto.aws stable main' && \
    sudo install-packages java-19-amazon-corretto-jdk graphviz