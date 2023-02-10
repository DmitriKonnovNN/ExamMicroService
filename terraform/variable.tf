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