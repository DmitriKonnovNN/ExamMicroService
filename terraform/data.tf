data "aws_iam_role" "ebs-service-role" {
  name = "aws-elasticbeanstalk-service-role"
}

data "aws_elastic_beanstalk_solution_stack" "docker-stack" {
  most_recent = true
  name_regex  = "^64bit Amazon Linux (.*) running Docker(.*)$"
}

#64bit Amazon Linux 2 v3.5.4 running Docker