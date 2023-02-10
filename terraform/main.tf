terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~>4.53.0"
    }
  }
  #   required_version = "~>0.12.0"
}

provider "aws" {
  profile = var.local_profile
  region  = var.aws_region
  default_tags {
    tags = {
      Owner = var.owner
    }
  }
}



resource "aws_elastic_beanstalk_application" "eb_app" {
  name        = "exams-management"
  description = "Exams management application"
  appversion_lifecycle {
    service_role          = data.aws_iam_role.ebs-service-role.arn
    max_count             = 20
    delete_source_from_s3 = false
  }

}

#resource "aws_elastic_beanstalk_application_version" "app_version" {
#  application = "exams-management"
#  bucket      = aws_s3_bucket.ebt_versions_bucket.id
#  key         = aws_s3_object.ebt_version_s3_obj.id
#  name        = "exams-management-version-label"
#  description = "application version created by terraform"
#}

resource "aws_elastic_beanstalk_environment" "exam_app_envs" {

  for_each               = toset(var.app_environments)
  name                   = each.value
  application            = aws_elastic_beanstalk_application.eb_app.name
  solution_stack_name    = data.aws_elastic_beanstalk_solution_stack.docker-stack.name
  cname_prefix           = "${each.value}-exam-management"
  tier                   = "WebServer"
  wait_for_ready_timeout = "30m"



  dynamic "setting" {
    for_each = var.optional_settings_by_namespaces
    content {
      name      = setting.value.option_name
      namespace = setting.value.namespace
      value     = setting.value.value
    }
  }
}

#resource "aws_s3_bucket" "ebt_versions_bucket" {
#  bucket = "${var.app_tag_name}-versions-bucket"
#}

#resource "aws_s3_object" "ebt_version_s3_obj" {
#  bucket = aws_s3_bucket.ebt_versions_bucket.id
#  key    = "beanstalk/bundle"
#}