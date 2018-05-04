#!/bin/bash
source settings.sh
aws s3 cp --recursive videos/ "s3://${S3_BUCKET}"