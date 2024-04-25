# Local kubernetes cluster에서 MSA 구동하기

## Summary
kubernetes on local 가이드 문서를 참고하여 환경을 구성하였다면 시작할 준비가 되었습니다.
만약 아직 로컬에서 kubernetes cluster가 설치되지 않았다면 먼저 설치하고 와주세요.

## Run private image registry
먼저 배포에 필요한 모든 서비스에 대한 이미지가 필요합니다. 현재 dev 설정은 이 로컬 상에서의 개인 이미지 저장소를 활용하는 것을 전제로 모든 설정이 되어있기 때문에 이 방법을 사용하는 것을 권장합니다.
구동은 현재 readme.md 파일이 존재하는 디렉터리의 `compose.yml`를 통해서 필요한 서비스를 구축해주세요!

## register image
gradle의 'jib' 테스크를 실행하여 개발한 서비스의 이미지를 개인 이미지 저장소에 등록해주세요.

```
$ ./gradlew jib
```

## deploy with helm chart

helm chart 을 통해서 배포를 진행해주세요. 반드시 dev 디렉터리에서 다음 명령을 실행해주세요!
```
$ helm install <release-name> .
```

## Finish
이제 모든 서비스가 배포되었습니다. 로컬에서 서비스를 확인해주세요!