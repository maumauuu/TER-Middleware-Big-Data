# TER-Middleware-Big-Data

## Sur Grid5000

Réserver le nombre de node dont vous avez besoin:

        oarsub -I -l nodes=$NBR_NODES,walltime=1:45 -t deploy
        
Déployer l'image avec docker présent dessus:

        kadeploy3 -f $OAR_NODE_FILE -a jessie-docker.env -k
        
On va ensuite construire la liste des workers:

        uniq $OAR_NODEFILE > liste.txt
        sed '1d' liste.txt > nodefile.txt
        
### Création du cluster

On effectue la commande suivante avec $NODE le premier node du fichier $OAR_NODEFILE:

        ssh root@$NODE 'docker swarm init'
        
Cette commande retourne une commande à executer pour rejoindre votre cluster. Faite une boucle
sur le fichier nodefile.txt pour executer la commande optenue à l'etape précédante:

        fichier="nodefile.txt"
        oldIFS=$IFS     # sauvegarde du séparateur de champ
        IFS=$'\n'       # nouveau séparateur de champ, le caractère fin de ligne

        for ligne in $(<$fichier)
        do
                ssh root@$ligne 'docker swarm join --token ${THE_TOKEN} ${IP_MASTER}:2377'
        done

Pour faciliter le deploiement des container, on va attribuer des rôles aux machines. Sur la machine maître on execute la commandes suivantes :

        docker node update --role "manager" ${HOSTNAME}

### Mise en place d'un sous-réseau local

Cette commande est optionnel, mais pour mieux cibler les machines sur lesquelles on va déployer les images, on crée un sous-réseaux local
à l'aide de cette commande:

         docker network create --driver overlay --subnet 10.0.0.0/22 spark

### Déploiement des images
#### Première méthode
Positionner vous dans le même dossier que le fichier spark.yml, situé sur la machine maître. Il va déployer les deux container (spark-master et spark-worker) sur les
noeuds du cluster

        docker stack deploy -c spark.yml spark
        
#### Deuxième méthode

Connectez vous sur le master du swarm cluster pour effectuer ses commandes:
 
        docker node update --role "manager" $MASTER
 
        docker service create \
        --name master \
        --constraint node.role==manager \
        --replicas 1 \
        --network spark \
        --publish "8080:8080" \
        --publish "7077:7077" \
        --publish "6066:6066" \
        gettyimages/spark:2.0.2-hadoop-2.7 \
        bin/spark-class org.apache.spark.deploy.master.Master
        
        docker service create \
        --name worker \
        --constraint node.role!=manager \
        --replicas $NBR_WORKER \
        --network spark \
        --publish "8081:8081" \
        gettyimages/spark:2.0.2-hadoop-2.7 \
        bin/spark-class org.apache.spark.deploy.worker.Worker spark://master:7077
        
Il faut attendre environ 15 minutes le temps que chaque node pull l'image de docker qu'il doit utiliser.

### Quitter le cluster

Il est important de quitter le cluster proprement pour une meilleure future utilisation de docker. Sur la machine maître, executer
la commande suivante pour supprimer les services :

          docker service rm ${SERVICE_MASTER}
          docker service rm ${SERVICE_WORKER}
          
 D'abord sur les machines différentes du master, executer la commande suivante :
 
          docker swarm leave --force
          
          
Puis executer la même commande sur la machine maitre, après avoir executer la commande suivante :

        docker network rm spark
