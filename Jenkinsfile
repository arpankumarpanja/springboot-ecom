pipeline {
    agent any

    environment {
        // Variables for your deployment
        EC2_USER = "ubuntu"
        EC2_IP = "16.16.166.43" // Replace with your EC2 Public IP
        SSH_CREDENTIAL_ID = "aws-ec2-key" // The ID from Jenkins credentials
        APP_DIR = "/home/ubuntu/springboot-app"
        EMAIL = "panjakras@gmail.com"
    }

    stages {
        // stage('Clone Repo') {
        //     steps {
        //         // Jenkins pulls the code from GitHub to your localhost
        //         git branch: 'main', url: 'https://github.com/your-username/your-repo.git'
        //     }
        // }

        stage('Deploy to AWS EC2') {
            steps {
                sshagent(credentials: [SSH_CREDENTIAL_ID]) {
                    
                    // SSH into EC2, check if the folder exists, clone or pull the repo, and run Docker Compose 
                    sh """
                    ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_IP} '
                        # Check if the directory already exists
                        if [ ! -d "${APP_DIR}" ]; then
                            # If it does not exist, clone the repo
                            git clone https://github.com/arpankumarpanja/springboot-ecom.git ${APP_DIR}
                            cd ${APP_DIR}
                            // git pull origin main
                            git checkout deploy
                            git pull origin monolithic
                        else
                            # If it does exist, navigate in and pull the latest changes
                            cd ${APP_DIR}
                            // git pull origin main
                            git checkout deploy
                            git pull origin monolithic
                        fi
                        
                        # Navigate to the app directory and restart the containers
                        cd ${APP_DIR}
                        docker compose down
                        docker compose up --build -d
                    '
                    """
                } 
            }
        }

        stage('Send Email Notification') {
            steps {
               emailext(
                subject: "Spring Boot App Deployed Successfully!",
                body: "Your app is live! API: http://${EC2_IP}:8088 | Adminer: http://${EC2_IP}:8081",
                to: "${EMAIL}"
               )
            }
        }
    }
}