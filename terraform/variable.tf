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
variable "beanstalk_iam_instance_profile" {
  type    = string
  default = "aws-elasticbeanstalk-ec2-role"
}
variable "beanstalk_environment_type" {
  type    = string
  default = "LoadBalanced"
}
variable "beanstalk_loadbalancer_type" {
  type    = string
  default = "application"
}
variable "session_stickiness" {
  type    = string
  default = "true"
}
variable "beanstalk_asg_min_size" {
  type    = string
  default = "1"
}
variable "beanstalk_asg_max_size" {
  type    = string
  default = "2"
}
variable "rds_size" {
  type    = string
  default = "10"
}
variable "rds_engine" {
  type    = string
  default = "postgres"
}
variable "rds_engine_version" {
  type    = string
  default = "12.8"
}
variable "rds_instance_class" {
  type    = string
  default = "db.t2.micro"
}
variable "rds_deletion_policy" {
  type    = string
  default = "Retain"
}
variable "rds_coupled_beanstalk_env" {
  type    = string
  default = "false"
}
variable "rds_multi_az" {
  type    = string
  default = "false"
}
variable "rds_password" {
  description = "password for db instance"
  type        = string
  sensitive   = true
}
variable "rds_username" {
  description = "username for db instance"
  type        = string
  default     = "ebroot"
}

