# cdkcli



## Installation

```sh
conda install -c https://repo.prefix.dev/zcp-forge cdk-cli
cdk-cli
```

## Dev


```
# run / test / build
clojure -M:run-m
clojure -T:build test
clojure -T:build ci


clojure -M:run-m depict <args>
```

## Update

1. bump version in build.clj
2. push a tag with `v**`
3. update `recipe.yaml` and `cdkcli.sh`in [zcp-forge](https://github.com/zachcp/zcp-forge/tree/main/cdk-cli)

## License

Copyright © 2025 zachcp
