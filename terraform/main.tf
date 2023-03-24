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
  tags = {
    Owner             = var.owner
    DevOpsEnvironment = each.value
  }

  dynamic "setting" {
    for_each = local.extensions_option_settings
    content {
      name      = setting.value["option_name"]
      namespace = setting.value["namespace"]
      value     = setting.value["value"]
    }
  }

  /*
Condition down below prevents from creating a database for each environment. A decoupled database with Retain deletion
    policy makes it possible to connect the remaining environments to DB afterwards.
*/

  dynamic "setting" {
    for_each = [
      for s in local.rds_settings : s
      if each.value == "development" || each.value == "staging"
    ]

    content {
      name      = setting.value["option_name"]
      namespace = setting.value["namespace"]
      value     = setting.value["value"]
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

locals {
  extensions_option_settings = [{
    namespace   = "aws:autoscaling:launchconfiguration",
    option_name = "IamInstanceProfile",
    value       = var.beanstalk_iam_instance_profile
    },
    {
      namespace   = "aws:elasticbeanstalk:environment",
      option_name = "EnvironmentType",
      value       = var.beanstalk_environment_type
    },
    {
      namespace   = "aws:elasticbeanstalk:environment",
      option_name = "LoadBalancerType",
      value       = var.beanstalk_loadbalancer_type
    },
    {
      namespace   = "aws:elasticbeanstalk:environment:process:default",
      option_name = "StickinessEnabled",
      value       = var.session_stickiness
    },
    {
      namespace   = "aws:autoscaling:asg",
      option_name = "MinSize",
      value       = var.beanstalk_asg_min_size
    },
    {
      namespace   = "aws:autoscaling:asg",
      option_name = "MaxSize",
      value       = var.beanstalk_asg_max_size
  }, ]
  rds_settings = [
    {
      namespace   = "aws:rds:dbinstance",
      option_name = "DBAllocatedStorage",
      value       = var.rds_size
    },
    {
      namespace   = "aws:rds:dbinstance",
      option_name = "DBEngine",
      value       = var.rds_engine
    },
    {
      namespace   = "aws:rds:dbinstance",
      option_name = "DBEngineVersion",
      value       = var.rds_engine_version
    },
    {
      namespace   = "aws:rds:dbinstance",
      option_name = "DBInstanceClass",
      value       = var.rds_instance_class
    },
    {
      namespace   = "aws:rds:dbinstance",
      option_name = "DBDeletionPolicy",
      value       = var.rds_deletion_policy
    },
    {
      namespace   = "aws:rds:dbinstance",
      option_name = "HasCoupledDatabase",
      value       = var.rds_coupled_beanstalk_env

    },
    {
      namespace   = "aws:rds:dbinstance",
      option_name = "MultiAZDatabase",
      value       = var.rds_multi_az
    },
    {
      namespace   = "aws:rds:dbinstance",
      option_name = "DBUser",
      value       = var.rds_username

    },
    {
      namespace   = "aws:rds:dbinstance",
      option_name = "DBPassword",
      value       = var.rds_password

    },
  ]
  empty_tuple = [{
    namespace   = "",
    option_name = "",
    value       = ""
  }]
}

# https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/command-options-general.html#command-options-general-rdsdbinstance
# https://aws.amazon.com/premiumsupport/knowledge-center/decouple-rds-from-beanstalk/