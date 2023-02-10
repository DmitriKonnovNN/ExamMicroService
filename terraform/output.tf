output "ebt_env_endpoint" {
  description = "Elastic Beanstalk Environment's Url Endpoint"
  value       = [for m in aws_elastic_beanstalk_environment.exam_app_envs : m.endpoint_url]
  sensitive   = false
}

output "solutions_stack" {
  description = "solutions-stack"
  value       = data.aws_elastic_beanstalk_solution_stack.docker-stack.name
}