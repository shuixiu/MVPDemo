package com.content.baseui.util.net;

public class NetInfo
{
    private String netName;
    private int netIp;
    private String netMac;

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        boolean b = obj instanceof NetInfo;
        if (!b)
        {
            return false;
        }
        NetInfo temp = (NetInfo) obj;
        if (this.netIp == temp.getNetIp()
                && this.netMac.equals(temp.getNetMac())
                && this.netName.equals(temp.getNetName()))
        {
            return true;
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "netIp:" + this.netIp + ", netMac:" + this.netMac + ", netName:"
                + this.netName;
    }

    public String getNetName()
    {
        return netName;
    }

    public void setNetName(String netName)
    {
        this.netName = netName;
    }

    public int getNetIp()
    {
        return netIp;
    }

    public void setNetIp(int netIp)
    {
        this.netIp = netIp;
    }

    public String getNetMac()
    {
        return netMac;
    }

    public void setNetMac(String netMac)
    {
        this.netMac = netMac;
    }
}
