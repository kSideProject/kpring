# Local kubernetes cluster에서 MSA 구동하기

## Summary
kubernetes on local 가이드 문서를 참고하여 환경을 구성하였다면 시작할 준비가 되었습니다.
만약 아직 로컬에서 kubernetes cluster가 설치되지 않았다면 먼저 설치하고 와주세요.

## Run private image registry
먼저 배포에 필요한 모든 서비스에 대한 이미지가 필요합니다. 현재 dev 설정은 이 로컬 상에서의 개인 이미지 저장소를 활용하는 것을 전제로 모든 설정이 되어있기 때문에 이 방법을 사용하는 것을 권장합니다.
구동은 현재 readme.md 파일이 존재하는 디렉터리의 `compose.yml`를 통해서 필요한 서비스를 구축해주세요!

## image build 
gradle의 'jib' 테스크를 실행하여 개발한 서비스의 이미지를 개인 이미지 저장소에 등록해주세요.

```shell
./gradlew jib
```

## deploy with helm chart

helm chart 을 통해서 배포를 진행해주세요. 반드시 infra 디렉터리에서 다음 명령을 실행해주세요!
```shell
helm dependency update
helm install <release-name> .
```

`helm dependency update` 명령은 helm이 의존하는 다른 helm을 설치하는 사전 작업입니다. nginx-ingress-controller 설치를 위해서 해당 과정이 `21-06-04` 기준으로 추가되었습니다.

다음은 windows powershell을 통해서 배포하는 예시입니다.
```shell
PS C:\Users\ydong\OneDrive\document\side_project\ksping> cd infra
PS C:\Users\ydong\OneDrive\document\side_project\ksping\infra> helm install dev .
Error: INSTALLATION FAILED: An error occurred while checking for chart dependencies. You may need to run `helm dependency build` to fetch missing dependencies: found in Chart.yaml, but missing in charts/ directory: ingress-nginx
PS C:\Users\ydong\OneDrive\document\side_project\ksping\infra> helm dependency build
Hang tight while we grab the latest from your chart repositories...
...Successfully got an update from the "elastic" chart repository
...Successfully got an update from the "nginx-ingress" chart repository
...Successfully got an update from the "sentry" chart repository
...Successfully got an update from the "bitnami" chart repository
...Successfully got an update from the "stable" chart repository
Update Complete. ⎈Happy Helming!⎈
Saving 1 charts
Downloading ingress-nginx from repo https://kubernetes.github.io/ingress-nginx
Deleting outdated charts
PS C:\Users\ydong\OneDrive\document\side_project\ksping\infra> helm install dev .
NAME: dev
LAST DEPLOYED: Tue Jun  4 21:10:41 2024
NAMESPACE: default
STATUS: deployed
REVISION: 1
```

## Finish
이제 모든 서비스가 배포되었습니다. 로컬에서 서비스를 확인해주세요!