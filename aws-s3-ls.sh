#!/bin/bash
source settings.sh
aws s3 ls "s3://${S3_BUCKET}"