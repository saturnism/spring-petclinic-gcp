# Enable Identity Aware Proxy
## Create Service Account
```
$ gcloud iam service-accounts create petclinic-cert-manager --display-name "PetClinic Cert Manager Service Account"
$ gcloud projects add-iam-policy-binding $PROJECT_ID \
     --member serviceAccount:petclinic-cert-manager@$PROJECT_ID.iam.gserviceaccount.com \
     --role roles/dns.admin
$ gcloud iam service-accounts keys create ~/petclinic-cert-manager.json \
    --iam-account petclinic-cert-manager@$PROJECT_ID.iam.gserviceaccount.com
```

Add Service Account to Secret
```
$ kubectl create secret generic petclinic-cert-manager-sa --from-file=$HOME/petclinic-cert-manager.json
```

## Setup Kube Cert Manager to provision Certificates
Install Cert Manager with Helm:
```
$ helm install \
    --name cert-manager \
    --namespace kube-system \
    stable/cert-manager
```

Create Issuer and Certificate:
```
$ kubectl apply -f IAP/
```

## Grab the TLS Cert/Key
If Kube Cert Manager worked, the TLS Cert/Key pair will be in the `petclinic-tls` secret:
```
$ kubectl get secret petclinic-tls -oyaml
```

You'll need to do your work to extract the key and cert from the base64 encoded values.

## Enable IAP
1. Go to [Cloud IAP Console](https://pantheon.corp.google.com/security/iap/project)
1. Configure OAuth Consent Screen

## Setup a HTTP(s) Load Balancer
1. Update Istio Ingress Node Port for the `http` port to 32000
1. Create the Cloud HTTPs Load Balancer to load balance GKE Cluster on port 32000
1. Apply the certificate/key you retrieved
1. Enable IAP on this Load Balancer
1. Add users to access IAP
