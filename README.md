# aws-meetup-2018-05-07-lambda-fargate-shake-that-shit
The demo project for Amsterdam AWS Meetup 2018-05-07 - lambda-fargate-shake-that-shit

## Demo
The demo is based on the [serverless blog - How to use AWS Fargate and Lambda for long-running processes in a Serverless app](https://serverless.com/blog/serverless-application-for-long-running-process-fargate-lambda/)
where an S3 upload triggers a lambda that triggers a Fargate batch job that extracts a thumbnail image from a video file.

## Installation
To install:

- In `ffmpeg-thumb-lambda` type: `sbt samDeploy`
- In `aws console`