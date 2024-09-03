#!/bin/bash

# # Navigate to your project directory
# cd /path/to/your/project || exit
git branch

# Prompt the user for a branch name
echo "Enter branch name (e.g., feature/user-authentication): "
read branchName

git status
# Prompt the user for a commit message
echo "Enter commit message: "
read commitMessage

echo "==================";
echo "list all remote :"
git remote
echo "==================";
# Prompt the user for the remote repository name
echo "Enter remote repository name (e.g., origin): "
read remoteName

# Check if the remote exists
if git remote show $remoteName >/dev/null 2>&1; then
    echo "Remote '$remoteName' exists."
else
    echo "'$remoteName' does not exist. Creating..."
    git remote add $remoteName https://github.com/username/repository.git
    echo "Please replace 'https://github.com/username/repository.git' with your actual repository URL."
fi

# Create and switch to the new branch
git checkout -b $branchName

# Stage all changes
git add .

# Commit the changes with the user-provided message
git commit -m "$commitMessage"

# Push the changes to the remote repository
git push $remoteName $branchName
