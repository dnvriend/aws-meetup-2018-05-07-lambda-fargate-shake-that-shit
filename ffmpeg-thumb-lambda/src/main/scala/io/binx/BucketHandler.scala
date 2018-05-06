package io.binx

import com.amazonaws.services.ecs.AmazonECS
import com.amazonaws.services.ecs.model.{ContainerOverride, KeyValuePair}
import com.github.dnvriend.ecs.ECS
import com.github.dnvriend.lambda.S3EventName.ObjectCreatedPut
import com.github.dnvriend.lambda._
import com.github.dnvriend.lambda.annotation.S3Conf
import com.github.dnvriend.lambda.annotation.policy.{AmazonECSTaskExecutionRolePolicy, AmazonECS_FullAccess, AmazonS3FullAccess, CloudWatchFullAccess}

import scala.collection.JavaConverters._
import scala.util.Try

@AmazonECS_FullAccess
@AmazonECSTaskExecutionRolePolicy
@CloudWatchFullAccess
@AmazonS3FullAccess
@S3Conf(
  bucketResourceName = "MeetupVideo",
  events = Array("s3:ObjectCreated:*"),
  reservedConcurrentExecutions = 1
)
class BucketHandler extends S3EventHandler {
  final val ClusterName = "shake-that-shit"
  final val TaskName = "ffmpeg-thumbs"
  final val ContainerName = "ffmpeg-thumbs"
  val client: AmazonECS = ECS.client()
  override def handle(events: List[S3Event], ctx: SamContext): Unit = {
    events.foreach {
      case event@S3Event(ObjectCreatedPut, eventSource, awsRegion, S3(Object(key, maybeSize), Bucket(arn, bucketName))) =>
        println(s"Processing: $event and launching task")
        val res = Try(ECS.runTask(client,
          cluster = ClusterName,
          taskDefinition = TaskName,
          count = 1,
          subnets = List("subnet-2e0dbf66"),
          securityGroups = List("sg-079e890cb982386bb"),
          assignPublicIp = true,
          containerOverrides = List(
            new ContainerOverride()
              .withName(ContainerName)
              .withEnvironment(List(
                new KeyValuePair().withName("AWS_REGION").withValue("eu-west-1"),
                new KeyValuePair().withName("INPUT_VIDEO_FILE_URL").withValue(s"https://s3-eu-west-1.amazonaws.com/io-binx-dnv-aws-meetup-2018-07-05-video/$key"),
                new KeyValuePair().withName("OUTPUT_S3_PATH").withValue("io-binx-dnv-aws-meetup-2018-07-05-thumbs"),
                new KeyValuePair().withName("OUTPUT_THUMBS_FILE_NAME").withValue(key.split("\\.").head + ".png"),
                new KeyValuePair().withName("POSITION_TIME_DURATION").withValue("00:01")
              ).asJava)
          )
        ))
        println(res.fold(t => { t.printStackTrace(); t.getMessage}, res => println(res)))
      case event => println(s"Unknown event $event")
    }
  }
}