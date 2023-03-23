data "aws_iam_role" "ebs-service-role" {
  name = "aws-elasticbeanstalk-service-role"
}

data "aws_elastic_beanstalk_solution_stack" "docker-stack" {
  most_recent = true
  name_regex  = "^64bit Amazon Linux (.*) running Docker(.*)$"
}

#64bit Amazon Linux 2 v3.5.4 running Docker

data "aws_db_instances" "rds_instance_production_identifier" {
  depends_on = [aws_elastic_beanstalk_environment.exam_app_envs["production"]]
  #  depends_on = aws_elastic_beanstalk_environment.exam_app_envs.name["production"]
  #  filter {
  #    name   = "db-instance-id"
  #    values = ["my-database-id"]
  #  }
}

data "aws_db_instance" "rds_endpoint" {
  db_instance_identifier = data.aws_db_instances.rds_instance_production_identifier.instance_identifiers[0]
}
