resource "aws_ssm_parameter" "db-connection" {
  name  = "/exam-management-rds/rdsstring"
  type  = "String"
  value = var.rds_connection_path
}

resource "aws_ssm_parameter" "db-password" {
  name  = "/exam-management-rds/rdspassword"
  type  = "SecureString"
  value = var.rds_password
}

resource "aws_ssm_parameter" "db-username" {
  name  = "/exam-management-rds/rdsusername"
  type  = "String"
  value = var.rds_username
}
