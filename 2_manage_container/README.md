# 2_manage_container

<a name="readme-top"></a>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-contents">About Contents</a></li>
    <li><a href="#h2-1-コンテナイメージの管理">H2-1 コンテナイメージの管理</a></li>
    <li><a href="#h2-2-コンテナの管理">H2-2 コンテナの管理</a></li>
  </ol>
</details>



<!-- ABOUT CONTENTS -->
## About Contents
このコンテンツはPodman入門ハンズオンの2つ目のコンテンツになります。
Podman を使ったコンテナの管理を学びます。
このコンテンツで実施するタスクは以下の２つです。

* **H2-1** コンテナイメージの管理
* **H2-2** コンテナの管理

このコンテンツではPodmanを使ってコンテナのライフサイクルを理解し、コンテナを管理できるようになることが目的です。

※もしまだPodmanの準備ができていない場合は[Getting Started](../README.md#getting-started)を参照してください。


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- H2-1 -->
## H2-1 コンテナイメージの管理

### **前提条件**

* [Getting Started](../README.md#getting-started)でセットアップが完了していること
* [1_run_container](./1_run_container/README.md#about-contents)のタスクを完了していること
* Podman machineが起動していること

### **ゴール**

* コンテナイメージの取得と破棄ができること

### **Task**

1. UBI(Red Hat Universal Base Image)を取得する

    * Red Hat Universal Base Image とは
     
         Red Hat Enterprise Linuxのサブセットで、OSSプロジェクトやISVがコンテナーイメージを配布するために利用することを意図して作成されています。 Red Hat UBI をベースとして作ったコンテナーイメージを RHEL および Red Hat OpenShift 上のコンテナーとして動かす場合には Red Hat からのサポートを受けることができます

    <br/>

    Red Hat Accountでregistry.redhat.io にログインする

      ```sh
      % podman login registry.redhat.io -u toomori@redhat.com
      Password: 
      Login Succeeded!
      ```
      * registry.redhat.io では Red Hat Ecosystem Catalogに掲載されているUBIなどを取得できます。 
      * 事前に[Red Hat ID](https://access.redhat.com/front) の取得を行ってください。[取得の参考はこちら](https://qiita.com/ynagashi/items/d22e3bca1519d13fcd21)
      * 可能であれば[Registry Service Accounts](https://access.redhat.com/terms-based-registry/#/)でのサインインが望ましいです
      * Red Hat IDアカウントの準備が不可能な場合は、H1-2の手順のようにして[docker.hubからのubi](https://hub.docker.com/r/redhat/ubi8)を取得してください

     <br/>

    UBIを取得する(pull)
      ```sh
      % podman search registry.redhat.io/ubi9 --limit 3  
      NAME                                DESCRIPTION
      registry.redhat.io/ubi9/openjdk-17-runtime  rhcc_registry.access.redhat.com_ubi9/openjdk...
      registry.redhat.io/ubi9/openjdk-11-runtime  rhcc_registry.access.redhat.com_ubi9/openjdk...
      registry.redhat.io/ubi9                     rhcc_registry.access.redhat.com_ubi9

      % podman pull registry.redhat.io/ubi9              
      Trying to pull registry.redhat.io/ubi9:latest...
      Getting image source signatures
      Checking if image destination supports signatures
      Copying blob sha256:ec3bd0502ca92cda6adc9345ef3fddcbde91be11b6ec7e31fd3901a89d0ba9a2
      Copying config 
      
      ...
      ```

     <br/>

    イメージが存在していることを確認する
      ```sh
      % podman images
      REPOSITORY               TAG         IMAGE ID      CREATED      SIZE
      docker.io/library/nginx  latest      835769b1adc0  5 days ago   139 MB
      registry.redhat.io/ubi9  latest      5211ceccc5aa  13 days ago  237 MB
      quay.io/podman/hello     latest      316717fcf920  5 weeks ago  85.1 kB
      ```
      * PodmanDesktopを開きImagesの項目を参照してください。取得したイメージが表示されます

     <br/>
     <br/>

2. イメージを破棄する
      
    コンテナイメージを利用しなくなった場合対象のイメージを破棄します

      ```sh
      % podman images
      REPOSITORY               TAG         IMAGE ID      CREATED      SIZE
      docker.io/library/nginx  latest      835769b1adc0  5 days ago   139 MB
      registry.redhat.io/ubi9  latest      5211ceccc5aa  13 days ago  237 MB
      quay.io/podman/hello     latest      316717fcf920  5 weeks ago  85.1 kB

      podman rmi 5211ceccc5aa
      Untagged: registry.redhat.io/ubi9:latest
      Deleted: 5211ceccc5aa24ba4a712f0a8487f5c0baf1a9959010fcad8640fb02f24b3979
      ```
      * IMAGE ID を指定して破棄することができます
      * **破棄したイメージは復旧できない**ので注意してください
      * containerが利用中のイメージは削除できません。この場合は先にコンテナの破棄を実施する必要があります

     <br/>
     <br/>

3. その他のイメージの操作について
    
    コンテナイメージは以下の様なコマンドをつかって他の環境やユーザと共有することができます

    * `podman push 'myimage' 'target_registry'` 
        
      レジストリにイメージをアップロード(push)します

    * `podman save -o 'output_path(.tar)' 'myimage' `

      イメージをファイルとして書き出します 

    * `podman load -i 'input_path(.tar)' `

      ファイルからイメージを復元します


      <br/>
      <br/>

これでタスクは完了になります

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- H2-2 -->
## H2-2 コンテナの管理

### **前提条件**

* H2-1が完了しており、Podman machineが起動していること

### **ゴール**

* Podmanからコンテナの状態を操作できる
* Execをつかってコンテナ内でコマンドを実行できる
* Podmanからコンテナの破棄ができる

### **Task**

* コンテナの状態について

    Podmanが扱うコンテナにはいくつかの状態があります。たとえば実行中の Running(Up) であったり、停止中の Stopped(Exited)などがあります。コンテナは常に稼働している状態ではなく、必要に応じて起動したり停止したりライフサイクルを管理することができます。


1. コンテナを実行する

    nginxコンテナの状態を確認する
      ```sh
      % podman ps -a
      CONTAINER ID  IMAGE                           COMMAND               CREATED     STATUS                 PORTS                 NAMES
      daf3ecd473a7  docker.io/library/nginx:latest  nginx -g daemon o...  2 days ago  Exited (0) 2 days ago  0.0.0.0:8080->80/tcp  ngx
      ```
     * `ps -a` で停止中のコンテナもふくめたすべてのコンテナを確認します
     * 該当のコンテナが見当たらない場合は H1-2を再度実施してください

    <br/>

    nginxのコンテナを開始する 
      ```sh
      % podman start ngx
      ngx
      % podman ps       
      CONTAINER ID  IMAGE                           COMMAND               CREATED     STATUS         PORTS                 NAMES
      daf3ecd473a7  docker.io/library/nginx:latest  nginx -g daemon o...  3 days ago  Up 2 days ago  0.0.0.0:8080->80/tcp  ngx

      ```
     * 該当のコンテナが見当たらない場合は H1-2を再度実施してください
     * `start 'container name or ID'` でコンテナを開始できます
     * `run` を実行した際のPORTS設定などはそのまま残っているのでstartには引数はありません
     * STATUSの項目がかわったことに注目してください
     * この状態でPodmanDesktopからImagesとContainersを確認してください。コンテナに必要なイメージと、実行中のコンテナがアクティブアイコンになっています
     * この状態で[http://localhost:8080](http://localhost:8080)へアクセスするとnginxが動いていることが確認できます
     
    <br/>
    <br/>

2. コンテナを停止する

      ```sh
      % podman stop ngx
      ngx
      % podman ps -a   
      CONTAINER ID  IMAGE                           COMMAND               CREATED     STATUS                 PORTS                 NAMES
      daf3ecd473a7  docker.io/library/nginx:latest  nginx -g daemon o...  3 days ago  Exited (0) 2 days ago  0.0.0.0:8080->80/tcp  ngx

    ```
     * `podman stop 'container name or ID'`でのコンテナを停止できます。
     * STATUSの項目がかわったことに注目してください
     * この状態でPodmanDesktopからImagesとContainersを確認してください。コンテナに必要なイメージはアクティブアイコンになっていますが、コンテナは非アクティブになっています
     * この状態で[http://localhost:8080](http://localhost:8080)へアクセスするとnginxが停止していることが確認できます

    <br/>
    <br/>

3. コンテナへのアクセス
    
    `podman exec` コマンドを使ってコンテナ内でコマンドを実行することができます。これは従来のコマンドと別のプロセスを発生させるため、コンテナ内にはいって変更を加えたり動作を確認する際に利用できます。（ただし、元となるコンテナイメージがマイクロコンテナなどの場合、意図するコマンドが利用できない場合もあります）

    コンテナを起動します
      ```sh
      % podman start ngx
      ngx
      ```
    
    <br/>

    コンテナ内でbashを実行します
      ```sh
      % podman exec -it ngx /bin/bash
      ```

    <br/>

    execが成功するとコンテナ内のシェルを操作できます。ここではnginxのindexファイルに修正を加えてみます

      ```sh
      root@daf3ecd473a7:/ echo 'Hello Nginx!!' >> /usr/share/nginx/html/index.html 
      ```
     * Hello Nginx!!のテキストを追記しました
     * この状態で[http://localhost:8080](http://localhost:8080)へアクセスするとページの最終行に『Hello Nginx!!』の文字が追記されたのがわかります

    <br/>

    コンテナ内のシェルを終了するとコンテナから抜け出すことができます
      ```sh
      root@daf3ecd473a7:/ exit
      % 
      ```

    <br/>

    コンテナを停止して再開させます。
      ```sh
      % podman stop ngx
      ngx
      % podman start ngx             
      ngx
      % podman ps -a                 
      CONTAINER ID  IMAGE                           COMMAND               CREATED     STATUS         PORTS                 NAMES
      daf3ecd473a7  docker.io/library/nginx:latest  nginx -g daemon o...  3 days ago  Up 2 days ago  0.0.0.0:8080->80/tcp  ngx
      ```
     * この状態で[http://localhost:8080](http://localhost:8080)へアクセスするとページの最終行に『Hello Nginx!!』の文字が追記されたのがわかります
     * つまり停止と再開を行った場合でもコンテナの変更は維持されています
     * CONTAINER IDが変化していない点に注目してください

      <br/>
      <br/>

4. コンテナの破棄
    
  
    コンテナを破棄するとコンテナに加えた変更も破棄されます。これを『コンテナの揮発性』といいます。k8sなどのオーケストレーターを利用する場合に、オーケストレーターはコンテナに異常を見つけると停止ではなく破棄が行われます。ここでは実際にどのようなことが起きるのかに注目してください

     <br/>

    コンテナを破棄します
      ```sh
      % podman ps -a                 
      CONTAINER ID  IMAGE                           COMMAND               CREATED     STATUS         PORTS                 NAMES
      daf3ecd473a7  docker.io/library/nginx:latest  nginx -g daemon o...  3 days ago  Up 2 days ago  0.0.0.0:8080->80/tcp  ngx
      % podman rm ngx
      Error: cannot remove container daf3ecd473a717d3905091143f1398b4409c2d45af2e6a5a7c7a3fecb4c10768 as it is running - running or paused containers cannot be removed without force: container state improper
      ```
      * `rm` でコンテナの破棄ができます。しかしここではエラーになってしまいます
      * 実行中(またはpauseによる一時停止中)のコンテナは削除することができません

      <br/>

    停止した後にもう一度破棄します
      ```sh
      podman stop ngx
      ngx
      % podman rm ngx  
      ngx
      % podman ps -a   
      CONTAINER ID  IMAGE       COMMAND     CREATED     STATUS      PORTS       NAMES
      ```
      * 今度は破棄されました。`ps -a` を実行しても起動/停止中のコンテナは見当たらないはずです

      <br/>

    最後に再びコンテナイメージからコンテナを実行します
    ```sh
    % podman run --name ngx -d -p 8080:80 nginx:latest
    fcfbaac90e49c9e2425d04a1399f97f5877ee74aa705de7e0cac23a612a92a43
    % podman ps -a                                    
    CONTAINER ID  IMAGE                           COMMAND               CREATED     STATUS         PORTS                 NAMES
    fcfbaac90e49  docker.io/library/nginx:latest  nginx -g daemon o...  2 days ago  Up 2 days ago  0.0.0.0:8080->80/tcp  ngx
    ```
     * H1-2での実行と違い、ローカルに存在しているnginxのコンテナイメージからコンテナが作られます
     * CONTAINER IDが変化した点に注目してください
     * この状態で[http://localhost:8080](http://localhost:8080)へアクセスするとページの最終行に『Hello Nginx!!』の文字が消去されたのがわかります。コンテナを破棄し新たなコンテナをイメージから焼き増したことにより、前工程で加えた変更も無かったことになっています。
     
      <br/>
      <br/>

これでタスクは完了になります


<p align="right">(<a href="#readme-top">back to top</a>)</p>


## 参考

1. [【UBI】Red Hatの新しい最軽量コンテナーイメージ：UBI Microの紹介](https://rheb.hatenablog.com/entry/ubi-micro)

1. [CentOS 7とPodmanで触ってみようRed Hat UBI - 導入編](https://rheb.hatenablog.com/entry/podman_on_centos7_with_ubi)

