package com.cloudogu.gop.cli

import io.micronaut.configuration.picocli.PicocliRunner
import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(name = 'gitops-playground-cli', description = 'CLI to create gop',
        mixinStandardHelpOptions = true)
class GitopsPlaygroundCli implements Runnable {
    // args group registry
    @Option(names = ['--registry-url'], description = 'The url of your external registry')
    private String registryUrl

    @Option(names = ['--registry-path'], description = 'Optional when --registry-url is set')
    private String registryPath

    @Option(names = ['--registry-username'], description = 'Optional when --registry-url is set')
    private String registryUsername

    @Option(names = ['--registry-password'], description = 'Optional when --registry-url is set')
    private String registryPassword

    @Option(names = ['--internal-registry-port'], description = 'Port of registry registry. Ignored when registry-url is set')
    private int internalRegistryPort

    // args group jenkins
    @Option(names = ['--jenkins-url'], description = 'The url of your external jenkins')
    private String jenkinsUrl

    @Option(names = ['--jenkins-username'], description = 'Mandatory when --jenkins-url is set')
    private String jenkinsUsername

    @Option(names = ['--jenkins-password'], description = 'Mandatory when --jenkins-url is set')
    private String jenkinsPassword

    // args group scm
    @Option(names = ['--scmm-url'], description = 'The host of your external scm-manager')
    private String scmmUrl

    @Option(names = ['--scmm-username'], description = 'Mandatory when --scmm-url is set')
    private String scmmUsername

    @Option(names = ['scmm-password'], description = 'Mandatory when --scmm-url is set')
    private String scmmPassword

    // args group remote
    @Option(names = ['--remote'], description = 'Install on remote Cluster e.g. gcp')
    private boolean remote

    private boolean insecure

    // args group tool configuration
    @Option(names = ['--kubectl-image'], description = 'Sets image for kubectl')
    private String kubectlImage
    @Option(names = ['--helm-image'], description = 'Sets image for helm')
    private String helmImage
    @Option(names = ['--kubeval-image'], description = 'Sets image for kubeval')
    private String kubevalImage
    @Option(names = ['--helmkubeval-image'], description = 'Sets image for helmkubeval')
    private String helmKubevalImage
    @Option(names = ['--yamllint-image'], description = 'Sets image for yamllint')
    private String yamllintImage
    @Option(names = ['--skip-helm-update'], description = 'Skips adding and updating helm repos')
    private boolean skipHelmUpdate
    @Option(names = ['--argocd-config-only'], description = 'Skips installing argo-cd. Applies ConfigMap and Application manifests to bootstrap existing argo-cd')
    private boolean argocdConfigOnly

    // args group metrics
    @Option(names = ['--metrics'], description = 'Installs the Kube-Prometheus-Stack for ArgoCD. This includes Prometheus, the Prometheus operator, Grafana and some extra resources')
    private boolean metrics

    // args group debug
    @Option(names = ['-d', '--debug'], description = 'Debug output')
    private boolean debug
    @Option(names = ['-x', '--trace'], description = 'Debug + Show each command executed (set -x)')
    private boolean trace

    // args group gop-configuration
    @Option(names = ['--username'], description = 'Set initial admin username')
    private String username
    @Option(names = ['--password'], description = 'Set initial admin passwords')
    private String password
    @Option(names = ['-y', '--yes'], description = 'Skip kubecontext confirmation')
    private boolean pipeYes

    // args group operator
    @Option(names = ['--fluxv1'], description = 'Install the Flux V1 module')
    private boolean fluxv1

    @Option(names = ['--fluxv2'], description = 'Install the Flux V2 module')
    private boolean fluxv2

    @Option(names = ['--argocd'], description = 'Install the ArgoCD module')
    private boolean argocd

    @Option(names = ['--argocd-url'], description = 'The URL where argocd is accessible. It has to be the full URL with http:// or https://')
    private String argocdUrl

    static void main(String[] args) throws Exception {
        PicocliRunner.run(GitopsPlaygroundCli.class, args)
    }

    @Override
    void run() {
        println this.toString()
        println "-----------------------------------------------"
        // TODO: executing commands should be done using picocli commandline interface
        // @see: https://picocli.info/#execute
        println "helm version".execute().text
    }

    @Override
    String toString() {
        return "GitopsPlaygroundCli{" +
                "registryUrl='" + registryUrl + '\'' +
                ", registryPath='" + registryPath + '\'' +
                ", registryUsername='" + registryUsername + '\'' +
                ", registryPassword='" + registryPassword + '\'' +
                ", internalRegistryPort=" + internalRegistryPort +
                ", jenkinsUrl='" + jenkinsUrl + '\'' +
                ", jenkinsUsername='" + jenkinsUsername + '\'' +
                ", jenkinsPassword='" + jenkinsPassword + '\'' +
                ", scmmUrl='" + scmmUrl + '\'' +
                ", scmmUsername='" + scmmUsername + '\'' +
                ", scmmPassword='" + scmmPassword + '\'' +
                ", remote=" + remote +
                ", insecure=" + insecure +
                ", kubectlImage='" + kubectlImage + '\'' +
                ", helmImage='" + helmImage + '\'' +
                ", kubevalImage='" + kubevalImage + '\'' +
                ", helmKubevalImage='" + helmKubevalImage + '\'' +
                ", yamllintImage='" + yamllintImage + '\'' +
                ", skipHelmUpdate=" + skipHelmUpdate +
                ", argocdConfigOnly=" + argocdConfigOnly +
                ", metrics=" + metrics +
                ", debug=" + debug +
                ", trace=" + trace +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pipeYes=" + pipeYes +
                ", fluxv1=" + fluxv1 +
                ", fluxv2=" + fluxv2 +
                ", argocd=" + argocd +
                ", argocdUrl='" + argocdUrl + '\'' +
                '}';
    }
}

