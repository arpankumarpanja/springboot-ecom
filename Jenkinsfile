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
        stage('Deploy to AWS EC2') {
            steps {
                // 1. We completely remove 'sshagent' and use 'withCredentials' instead
                withCredentials([sshUserPrivateKey(credentialsId: SSH_CREDENTIAL_ID, keyFileVariable: 'KEY_FILE', usernameVariable: 'USER')]) {
                    
                    // 2. We use the raw ssh command with the -i flag pointing to the temporary KEY_FILE 
                    sh """
                    ssh -i ${KEY_FILE} -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_IP} '
                        # Check if the directory already exists
                        if [ ! -d "${APP_DIR}" ]; then
                            # If it does not exist, clone the repo
                            git clone https://github.com/arpankumarpanja/springboot-ecom.git ${APP_DIR}
                            cd ${APP_DIR}
                            # git pull origin main (NOTE: Changed // to # for bash comments)
                            git checkout deploy
                            git pull origin monolithic
                        else
                            # If it does exist, navigate in and pull the latest changes
                            cd ${APP_DIR}
                            # git pull origin main (NOTE: Changed // to # for bash comments)
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