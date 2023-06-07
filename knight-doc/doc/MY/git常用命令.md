一般提交代码
git add .

git commit -a
ps:添加注释 -->Ctrl+X-->Y

git push origin HEAD:refs/for/master


更新代码：
git pull --rebase

master变更合入dev：
切到dev目录
git checkout dev

进行merge操作：
git merge master --no-ff -m"合入公共对象等"

提交：
git push

拉分支：
git push --set-upstream origin dev

// 抓取origin仓库master分支的代码
git fetch origin master

// 将origin仓库master分支的代码与当前分支的代码合并(先fetch再merge)
git merge origin/master

// 将origin仓库master分支的代码与当前分支的代码强制合并
git merge origin/master --allow-unrelated-histories

// 查看合并后的情况（包括冲突文件）
git diff


本地新切分支：
git checkout -b dev

git branch -a

git push --set-upstream origin dev

添加/提交代码
git add .

git commit -a
ps:添加注释 -->Ctrl+X-->Y

git push origin HEAD:refs/for/dev




git fetch --all   //只是下载远程内容，不做任何合并  
git reset --hard origin/master    //把HEAD指向刚下载的最新版本




error: 无法推送一些引用到 'ssh://zhang_ertong@10.30.37.201:29418/iot/heimdall-web'
zhangertong@zetpc:~/zhangertong/code/iot/heimdall-web$ git status
位于分支 master
您的分支与上游分支 'origin/master' 一致。
无文件要提交，干净的工作区
zhangertong@zetpc:~/zhangertong/code/iot/heimdall-web$ git checkout -b dev
切换到一个新分支 'dev'
zhangertong@zetpc:~/zhangertong/code/iot/heimdall-web$ git branch -a
* dev
  master
  remotes/origin/HEAD -> origin/master
  remotes/origin/master
zhangertong@zetpc:~/zhangertong/code/iot/heimdall-web$ git push --set-upstream origin dev
Total 0 (delta 0), reused 0 (delta 0)
remote: error: branch refs/heads/dev:
remote: You need 'Create' rights to create new references.
remote: User: zhang_ertong
remote: Contact an administrator to fix the permissions
remote: Processing changes: refs: 1, done    
To ssh://zhang_ertong@10.30.37.201:29418/iot/heimdall-web
! [remote rejected] dev -> dev (prohibited by Gerrit: not permitted: create)
error: 无法推送一些引用到 'ssh://zhang_ertong@10.30.37.201:29418/iot/heimdall-web'

zhangertong@zetpc:~/zhangertong/code/iot/heimdall-web$ git push --set-upstream origin dev

Total 0 (delta 0), reused 0 (delta 0)
remote: Processing changes: refs: 1, done    
To ssh://zhang_ertong@10.30.37.201:29418/iot/heimdall-web
* [new branch]      dev -> dev
分支 dev 设置为跟踪来自 origin 的远程分支 dev。




