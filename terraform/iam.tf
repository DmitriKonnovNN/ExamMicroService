data "aws_iam_role" "elastic_beanstalk_ec2_role" {
  name = "aws-elasticbeanstalk-ec2-role"
}

data "aws_iam_policy" "ssm_read_only_access" {
  name = "AmazonSSMReadOnlyAccess"
}

resource "aws_iam_role_policy_attachment" "ssm_read_only_policy_attach_to_ec2_role" {
  role       = data.aws_iam_role.elastic_beanstalk_ec2_role.name
  policy_arn = data.aws_iam_policy.ssm_read_only_access.arn
}