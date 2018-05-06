#!/usr/bin/env python3
import sys
import os
import glob
import subprocess

# aws s3 cp --recursive videos/ "s3://${S3_BUCKET}"
def copy_files():
    for file in list(glob.glob('videos/*.mp4')):
        for i in range(0, int(sys.argv[1])):
            file_name = file.split('/')[1].split('.')[0]
            new_file_name = file_name + str(i) + ".mp4"
            subprocess.run(["aws", "s3", "cp", file, "s3://io-binx-dnv-aws-meetup-2018-07-05-video/" + new_file_name])


copy_files()

