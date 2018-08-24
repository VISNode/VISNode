commandCp=""

for i in $(ls -d lib/*.jar); do 
    if [ "$commandCp" != "" ]; then
        commandCp="$commandCp:";
    fi
    commandCp="$commandCp${i%%/}"; 
done

java -cp "$commandCp" visnode.application.VISNode $@