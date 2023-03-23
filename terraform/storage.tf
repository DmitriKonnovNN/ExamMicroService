resource "aws_ssm_parameter" "db-connection" {
  name  = "/config/exam-management-service/rdsstring"
  type  = "String"
  value = data.aws_db_instance.rds_endpoint.endpoint
}


resource "aws_ssm_parameter" "db-password" {
  name  = "/config/exam-management-service/rdspassword"
  type  = "SecureString"
  value = var.rds_password
}

resource "aws_ssm_parameter" "db-username" {
  name  = "/config/exam-management-service/rdsusername"
  type  = "String"
  value = var.rds_username
}
resource "aws_s3_bucket" "exam_management_service_S3_bucket_dev" {
  bucket = "exam_management_service_dev"
}

resource "aws_s3_bucket_policy" "exam_management_service_s3_policy" {
  bucket = aws_s3_bucket.exam_management_service_S3_bucket_dev.id
  policy = ""
}

resource "aws_s3_bucket_versioning" "exam_management_service_s3_v" {
  bucket = aws_s3_bucket.exam_management_service_S3_bucket_dev.id
  versioning_configuration {
    status = "Enabled"
  }
}
resource "aws_s3_bucket_cors_configuration" "exam_management_service_s3_cors" {

  bucket = aws_s3_bucket.exam_management_service_S3_bucket_dev.id
  cors_rule {
    allowed_headers = ["*"]
    allowed_methods = ["GET"]
    allowed_origins = [aws_elastic_beanstalk_environment.exam_app_envs["development"].cname]
    expose_headers  = ["ETag"] // ????
    max_age_seconds = 3000

  }
}


resource "aws_s3_bucket_public_access_block" "example" {
  bucket = aws_s3_bucket.exam_management_service_S3_bucket_dev.id

  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}


#resource "aws_db_instance" "rds-standard-instance" {
#  allocated_storage    = 10
#  db_name              = "postgres"
#  engine               = "mysql"
#  engine_version       = "5.7"
#  instance_class       = "db.t3.micro"
#  username             = "foo"
#  password             = "foobarbaz"
#  parameter_group_name = "default.mysql5.7"
#  skip_final_snapshot  = true
#}