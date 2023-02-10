variable "owner" {
  type = string
}

variable "app_tag_name" {
  description = "Enter tag name for all resources to be built"
  type        = string
}
variable "aws_region" {
  type        = string
  description = "AWS region"
}
variable "local_profile" {
  description = "local aws cli profile"
  type        = string
}

variable "app_main_port" {
  description = "standard port web server listens to"
  type        = number
}

variable "app_environments" {
  description = "elasticbeanstalk's environments"
  type        = list(string)
  default     = ["production", "staging", "development"]

}

variable "rds_connection_path" {
  description = "path to get connection to RDS"
  type        = string
}

variable "rds_password" {
  description = "password for db instance"
  type        = string
  sensitive   = true
}

variable "rds_username" {
  description = "username for db instance"
  type        = string
  default     = "dkuser"
}

variable "optional_settings_by_namespaces" {
  type = map(object({
    namespace   = string,
    option_name = string,
    value       = string
  }))
  default = {
    "ASG_LAUNCH_CONFIG" = {
      namespace   = "aws:autoscaling:launchconfiguration",
      option_name = "IamInstanceProfile",
      value       = "aws-elasticbeanstalk-ec2-role"
    },
    "ELB_ENV_TYPE" = {
      namespace   = "aws:elasticbeanstalk:environment",
      option_name = "EnvironmentType",
      value       = "LoadBalanced"
    },
    "ELB_TYPE" = {
      namespace   = "aws:elasticbeanstalk:environment",
      option_name = "LoadBalancerType",
      value       = "application"
    },
    "SESSION_STICKINESS" = {
      namespace   = "aws:elasticbeanstalk:environment:process:default",
      option_name = "StickinessEnabled",
      value       = "true"
    },


  }

}