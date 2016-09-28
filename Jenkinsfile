node {
    echo 'Stopping any existing postgres'
    def dockerHome = tool 'DOCKER'
    sh "${dockerHome}/bin/docker rm -f dvdstore-db"
    sh "${dockerHome}/bin/docker run --name dvdstore-db -p 5432:5432 -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=dellstore2 -d scottseo/dvdstore-db"
}