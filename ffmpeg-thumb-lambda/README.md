# ffmpeg-thumb-lambda
A Lambda that is triggered by S3 and launches an [fmpeg-thumbs](https://github.com/dnvriend/docker-ffmpeg-thumb) container 
in [AWS Fargate](https://aws.amazon.com/fargate/) to extract a thumbnail from the video and uploads the thumbnail to an S3 bucket.

## Installation
To deploy type:

```
$ sbt samDeploy
```

