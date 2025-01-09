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


clojure -M:run-m convertsmiles  -s "CCCSOSCC" -o  temp.svg
clojure -M:run-m depict --smiles "CCCCC"  --fmt svg --style bow --output out.svg
clojure -M:run-m depict --smiles "O[C@@H]1CC[C@H](C[C@H]1OC)C[C@@H](C)[C@@H]4CC(=O)[C@H](C)/C=C(\C)[C@@H](O)[C@@H](OC)C(=O)[C@H](C)C[C@H](C)\C=C\C=C\C=C(/C)[C@@H](OC)C[C@@H]2CC[C@@H](C)[C@@](O)(O2)C(=O)C(=O)N3CCCC[C@H]3C(=O)O4"  --fmt png --style cow --output out.png
```

## Update

1. bump version in build.clj
2. push a tag with `v**`
3. update `recipe.yaml` and `cdkcli.sh`in [zcp-forge](https://github.com/zachcp/zcp-forge/tree/main/cdk-cli)

## License

Copyright © 2025 zachcp
